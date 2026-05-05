# Build de Angular
FROM node:20 AS build
WORKDIR /app

# Ruta del package.json de forma manual
COPY Frontend/apptienda/package*.json ./
RUN npm install

COPY Frontend/apptienda ./
RUN npm run build -- --configuration production

# Servir con Nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
