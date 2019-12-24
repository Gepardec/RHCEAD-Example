FROM jboss/base-jdk:11

ENV JBOSS_HOME="/jboss-eap-7.2" \
    JAVA_OPTS="-Djava.io.tmpdir=/tmp -Djava.net.preferIPv4Stack=true -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

ADD ./jboss-eap-7.2.0.zip /tmp/eap.zip
ADD ./config.cli /tmp/config.cli

USER root

RUN yum update -y \
    && yum clean all \
    && unzip /tmp/eap.zip -d / \
    && mv /tmp/config.cli ${JBOSS_HOME}/config.cli \
    && chown -R 1001:0 ${JBOSS_HOME} \
    && chmod -R 770 ${JBOSS_HOME} \
    && ${JBOSS_HOME}/bin/add-user.sh admin admin!123 \
    && ${JBOSS_HOME}/bin/jboss-cli.sh --file=${JBOSS_HOME}/config.cli \
    && rm -rf /tmp/*

WORKDIR ${JBOSS_HOME}

USER 1001

EXPOSE 8080 8443 9990 9999

ENTRYPOINT ["/bin/sh", "-c"]

CMD ["${JBOSS_HOME}/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0"]