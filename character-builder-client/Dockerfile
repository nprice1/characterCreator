FROM node:16-slim as builder

WORKDIR /app

# Doing this up front ensures this is only run when package.json changes
COPY package*.json ./
RUN npm install

COPY tsconfig.json ./
COPY public/* public/
COPY src/ src/
RUN npm run build

FROM nginx:alpine
# Set working directory to nginx asset directory
WORKDIR /usr/share/nginx/html
# Remove default nginx static assets
RUN rm -rf ./*
# Copy static assets from builder stage
COPY --from=builder /app/build .
# Containers run nginx with global directives and daemon off
ENTRYPOINT ["nginx", "-g", "daemon off;"]
