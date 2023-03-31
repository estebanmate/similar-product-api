# Backend dev technical test

**similar-product-api** is an application that exposes the agreed REST API on port 5000.

### Create the image for App:
Create docker image for similar-products-api application
```
docker build -t similar-products-api .
```
Run **similar-products-api** image
```
docker run --name container-similar-products-api --publish 5000:5000 similar-products-api
```
For access the openapi:
[http://localhost:5000/swagger-ui.html](http://localhost:5000/swagger-ui.html)

### Test and Mocks
Download https://github.com/dalogax/backendDevTest

Enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

Then you can start the mocks and other needed infrastructure with the following command.
```
docker-compose up -d simulado influxdb grafana
```
Check that mocks are working with a sample request to 
    [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids)

Check that similar-products-api are working with a sample request to
    [http://localhost:5000/product/1/similar](http://localhost:5000/product/1/similar)
    
To execute the test run:
```
docker-compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the results.
