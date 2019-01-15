---
title: Automating deployments with Docker Hub builds
slug: automating-docker-hub-deployments
date: 2018-01-14
layout: post.hbs
description:
            Automating deployments is a common task that must be done. Docker Hub has a built-in
            automated build system that will build your Dockerfiles given a GitHub repo.
            Thanks to webhooks, newly built images can be automatically deployed to your Kubernetes cluster.
---

As part of creating this blog, I explored options for automating the build and deployment. My first thought was to use
a CI system such as [Travis](https://travis-ci.org/) since it is free for public GitHub repos. I decided against it, not because it was
a bad choice, but because I noticed that Docker Hub has an automated build system. Since I'm building Docker images anyway,
I figured I'd take out a step.

I decided that involving one less service would be a convenient way to go. With that said, I needed a way to actually deploy new
images pushed to Docker Hub. Thankfully, Docker Hub provides can trigger a webhook when new images are pushed.
Using the webhook, I was able to build a simple web service that deploys the updated image. It runs on a Kubernetes cluster and updates a deployment whenever
a new image is pushed to the repository.

## Configuring the GitHub repo

An issue I ran into is that Kubernetes will not perform a rolling update when the new and old images have the same name. For example,
if I push an image tagged `latest` to my Docker Hub repo, then it won't actually be deployed to the cluster. To deal with this, I tag each image
with the commit sha of the commit that is being built. Unfortunately, this can't be done from Docker Hub's UI, so you will have to create [hooks](https://docs.docker.com/docker-hub/builds/advanced/#custom-build-phase-hooks).

In the same directory as your Dockerfile, create a new directory called `hooks`. Then create a file called [`hooks/post_push`](https://github.com/proubatsis/blog/blob/master/blog-site/hooks/post_push) with the following contents:

```
#!/bin/bash

docker tag $IMAGE_NAME $DOCKER_REPO:$SOURCE_COMMIT
docker push $DOCKER_REPO:$SOURCE_COMMIT
```

After you Docker Hub builds your image, the `post_hook` script is run and it will tag your image with
the git commit sha and push it to your Docker Hub repo. This will allow Kubernetes to differentiate between the old and new images so that
it will notice and perform a rolling update.

## Reacting to Webhooks

To react to Webhooks I built a simple web service using ASP.NET Core. I don't have much experience with ASP.NET, but it's a framework I'd like to
experiment with some more. I saw this as a good chance to try it out. To summarise, I created a project called [docker-hub-kube-deploy](https://github.com/proubatsis/docker-hub-kube-deploy). It exposes and endpoint at `/api/docker-hub` that accepts `POST` requests in the format
outlined in Docker Hub's [documentation](https://docs.docker.com/docker-hub/webhooks/#example-webhook-payload). When a new image is pushed
to Docker Hub, this endpoint is called. When the endpoint is invoked, it ensures that the image is not tagged `latest`. We don't want
images tagged `latest` to be deployed. If it's not tagged `latest`, then the process continues. Next, it checks which Kubernetes deployment the image maps to.
Then, it updates the first container in the deployment with the new image. There is an easy to use [Kubernetes Client](https://github.com/kubernetes-client/csharp) for C#. It provides an easy connection to the Kubernetes API from inside of a pod.

The tricky part to this was learning about RBAC. With the default service account, the pod running this deploy tool will not be able to
update your deployment. To allow it to update your deployment you need to create a service account, a role that gives access to deployments,
and a role binding to connect your service account to the role. Once this is done, you can assign your service account to your deployment.

```
apiVersion: apps/v1
kind: Deployment
spec:
  template:
    spec:
      serviceAccountName: my-service-account-name
```

This process can be simplified by packaging your Kubernetes resources in a [Helm](https://helm.sh/) chart. Helm uses Go templating to
generate Kubernetes yaml files based on various default and user specified values. As a result, to deploy this service onto your own
cluster:

1. Install Helm
2. Clone the repo
3. Run the following command substituting the release name, namespace, and mappings where appropriate:

```
helm install --name my-release-name --namespace my-namespace \
--set secret.mapping[0].image="docker-hub-repo/image-name" \
--set secret.mapping[0].deployment="kubernetes-deployment-to-updated" \
--set secret.mapping[1].image="docker-hub-repo/other-image" \
--set secret.mapping[1].deployment="other-deployment" \
./helm-chart
```

This makes things convenient if I want to deploy another instance for other projects. I have a standard way of installing it without much hassle.

## Take Aways

This was a fun experiment, and I think I'll use this approach for the foreseeable future. I like that I don't need any third party services
in additon to Docker Hub. The main issue I have with this approach is that Docker Hub builds take very long.
I'm not too worried about it since this is for a hobby project, but I could see it causing annoyance if used in a more critical setting.
