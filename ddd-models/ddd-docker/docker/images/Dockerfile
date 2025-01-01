##get parent (base) image as start of new image
#FROM debian:latest
##create a new path in an image
#RUN mkdir --parent /home/yun900800
##copy host data to image
#COPY file /home/yun900800/file
##download package updates to the image
#RUN apt-get update
##perform installation
#RUN apt-get install -y vim
##set the main executable
#CMD ["vim","--version"]
FROM debian:latest
RUN mkdir --parents /home/yun900800/ && \
    apt-get update && \
    apt-get install -y vim
COPY file /home/yun900800/file
CMD ["vim", "--version"]