FROM trion/ng-cli as build-step
WORKDIR /app
COPY ./frontend-adminpanel/package.json ./
RUN npm install
COPY ./frontend-adminpanel .
EXPOSE 4201
CMD npm run start
