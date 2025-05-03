FROM apache/kafka:4.0.0

USER root

WORKDIR /opt/kafka

EXPOSE 8083

RUN mkdir /opt/kafka/plugins

COPY plugins.sh .
RUN sh plugins.sh

# Copy connector archive files and extract them
COPY ./connectors/*.zip ./connectors/*.tar.gz /tmp/

RUN ls -ll /tmp

# Extract ZIP files
RUN for zipfile in /tmp/*.zip; do \
      if [ -f "$zipfile" ]; then \
        unzip -o "$zipfile" -d /opt/kafka/plugins/ && \
        echo "Extracted $zipfile"; \
      fi; \
    done

# Extract TAR.GZ files
RUN for tarfile in /tmp/*.tar.gz; do \
      if [ -f "$tarfile" ]; then \
        mkdir -p /tmp/extract && \
        tar -xzf "$tarfile" -C /tmp/extract && \
        cp -r /tmp/extract/* /opt/kafka/plugins/ && \
        rm -rf /tmp/extract && \
        echo "Extracted $tarfile"; \
      fi; \
    done && \
    chmod -R 777 /tmp/* && rm -f /tmp/*.zip /tmp/*.tar.gz

COPY entrypoint.sh .

#RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
CMD ["start"]