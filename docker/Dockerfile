# sudo docker build . -t zisk0/custom_nginx
# sudo docker run -p 8080:80 -v /home/zisk0/dev/cep_workshop/docker/data/nginx/log:/var/log/nginx/ zisk0/custom_nginx

# Use the official nginx image
FROM nginx

# Copy the nginx configuration
COPY conf/nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Make port 80 available to the world outside this container. We will bind it to 8080
EXPOSE 80/tcp

# We will bind data/nginx/log/ to this volume in the docker run command
VOLUME /var/log/nginx/

# Run app.py when the container launches
CMD ["nginx", "-g", "daemon off;"]
