FROM node:14-slim

COPY package*.json ./
COPY tsconfig.json ./
COPY public/* public/
COPY src/ src/

RUN npm install

EXPOSE 3000

CMD [ "npm", "start" ]
