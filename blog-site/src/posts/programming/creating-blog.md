---
title: Creating a blog
slug: creating-a-blog
date: 2018-10-14
layout: post.hbs
description:
            Creating a blog is something that I believe is long overdue for me.
            It is a way to document and share my thoughts, and most importantly
            it is somewhere that I can go and reflect on how things have changed over time.
---

Creating a blog is always something that I've wanted to try but never got around to. I see all the programming blogs available online and it is clear how much they have helped me learn over the years. I hope that writing a blog will be beneficial for me as well. It gives me a place to document my thoughts on any projects that I build over time. I hope that it will help others learn as many blogs out there have done for me.

With that said, I could have used an existing blogging platform such as Wordpress. Another issue I see with using an existing platform is that as a developer it feels essential to build your own blog from the ground up. That is even more important if you're writing a blog with a focus on web development. Building a blog from scratch is in itself a worthwhile learning experience.

## Choosing an approach

The first decision I made was if I wanted to build a static or dynamic blog. The answer was clear to me that I want to build the blog as a static site. My reasoning behind this is that it's easier to build and maintain. I am comfortable with git and Markdown so it is easy for me to create or edit posts. If someone that is not comfortable with these tools is publishing posts, then a more traditional blog may be a better choice.

After deciding that I want to build a static site, choosing a static site generator is a logical next step. I evaluated four main options and ended up choosing [Metalsmith](https://metalsmith.io/).

### Jekyll

[Jekyll](https://jekyllrb.com/) is the most popular of the options I investigated. Jekyll is a framework that has merit due to the many website that are built on it. My only critical concern was that many complaints exist from its users about slow build times for large projects. A minor issue I have with Jekyll is that it requires me to install Ruby. Ruby is something I rarely use and I did not want to install it only for Jekyll.

### Hugo

[Hugo](https://gohugo.io/) is another very popular option. All in all it appears to be a solid framework and I did not have any real complaints about it. I did not end up choosing Hugo because I found Metalsmith more appealing for the freedom it gives me. If I  build a larger and more complex site then I may appreciate what Hugo has to offer. More established patterns along with the features and themes Hugo offers are compelling.

### Metalsmith

[Metalsmith](https://metalsmith.io/) is not as popular as the previous two static site generators. It gives you far more freedom and it is very customizable. It takes very little effort to get a simple site up and running. All the features it provides are from plugins. You only need to include as few npm modules as are necessary to build your site.

### Gulp

[Gulp](https://gulpjs.com/) is not a static site generator, it's more of a build automation system. The reason I evaluated gulp as an option is because I have experience with it and I thought it could easily act as a static site generator. I did not choose it because I appreciated the simplicity Metalsmith offered.

## Future additions to the blog

My priority in building the blog is to get a static site ready where I can create some posts. Everything else is secondary. With that said, I have plans to add search functionality to the site. This will become more important as I write more posts. Analytics will be key to gain insights into my audience. Commenting may be another important feature to encourage discussion about the blog's content.

A nice standard that has come along recently is web components. They give you the ability to create custom HTML elements. You can create your components using the official API. Or you can build a component in your favourite front end framework such as React and then distribute it as a web component. The great thing is that I can experiment this way. This blog is a collection of HTML documents, I can drop in a web component wherever I want with ease. It doesn't matter if one is written with vanilla JavaScript, or another one is written with something different such as [Elm](https://elm-lang.org/). The idea is that web components provide a easy way to include a custom component as if it is a standard HTML tag.

Any rest services that I need create down the line such as for search or comments will likely be written in [Scala](https://www.scala-lang.org/) with the [http4s](https://http4s.org/) library. I've tried developing REST services using many different options at this point (eg. Node.js, Python/Flask, .NET Core, Java EE). I've found that the combination of Scala and http4s is the most pleasant option for me. Scala has a strong community behind it, and anything that's not available you can easily create a wrapper for from an existing Java library. I appreciate the functional programming style, but I also appreciate that Scala is not overly strict about it. Scala combines the object oriented model well with the functional model in a way that is pleasant to use.

I've also used Node.js and Java EE quite a bit at this point. Node.js allows me to build things quickly and thus is my go to language for something like a hackathon, but I am not a fan of how loose it is. In particular I am not fond of duck typing; especially as a project grows. Duck typing is the idea that "if it walks like a duck and quacks likes a duck then it must be a duck". JavaScript objects are not "defined" in any way, they just exist. For example, you "expect" that a person has a name there's no guarantee that a name field actually exists in your object. Most languages do not work this way, imagine if Java did not have classes but only objects. You would not know what methods or members an object contains. Duck typing is a huge advantage in time-sensitive situations such as a competition. While Node.js has had huge success in industry I believe that other options should be evaluated for longer term complex projects.

As for Java EE, it is not a bad choice and it has been changing for the better. It is something I started using a bit later on, and thus found its way of doing things a bit odd. Java EE is something I'd consider, but based on my limited experience with .NET Core I think it is something that I'd enjoy more. The way of doing things in .NET Core seems to be more well-defined and in some ways more streamlined. One concrete example I can give is database access. Java EE has JPA. I found JPA tricky to setup and a bit clumsy to perform queries. In comparison, using entity framework the setup was much simpler and the queries were a pleasure to use thanks to LINQ. With that said I do not have too much experience with .NET Core, but it is something I'm very interested to explore further.
