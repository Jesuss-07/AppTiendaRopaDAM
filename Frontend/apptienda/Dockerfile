# Build de Angular
FROM node:20 AS build
WORKDIR /app

# 👇 YA NO pongas Frontend/apptienda
COPY package*.json ./
RUN npm install

COPY . ./
RUN npm run build -- --configuration production

RUN ls -R /app/dist

# Servir con Nginx
FROM nginx:alpine
COPY --from=build /app/dist/apptienda/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]