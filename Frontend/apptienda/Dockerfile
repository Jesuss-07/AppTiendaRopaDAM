FROM node:20 AS build
WORKDIR /app

COPY Frontend/apptienda/package*.json ./
RUN npm install

COPY Frontend/apptienda/ ./
RUN npm run build -- --configuration production

FROM nginx:alpine

COPY --from=build /app/dist/apptienda/browser /usr/share/nginx/html

COPY Frontend/apptienda/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]