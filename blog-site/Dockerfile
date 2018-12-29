FROM node:8

WORKDIR /var/app
COPY . .

RUN npm install && npm run build

FROM nginx:alpine

COPY --from=0 /var/app/dist/ /usr/share/nginx/html/
