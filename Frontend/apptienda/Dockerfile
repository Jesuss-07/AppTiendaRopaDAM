# Build de Angular
FROM node:20 AS build
WORKDIR /app

# Copiar package.json desde la carpeta correcta
COPY Frontend/apptienda/package*.json ./
RUN npm install

# Copiar el resto del frontend
COPY Frontend/apptienda/ ./
RUN npm run build -- --configuration production

# Servir con Nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
