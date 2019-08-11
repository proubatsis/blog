FROM node:10

ARG analyticsId
ENV GOOGLE_ANALYTICS_TRACKING_ID=${analyticsId}

WORKDIR /var/app
COPY . .

RUN npm install && npm run build

FROM nginx:alpine

COPY --from=0 /var/app/dist/ /usr/share/nginx/html/
