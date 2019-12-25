FROM jboss/base-jdk:11

ENV JBOSS_HOME="/opt/jboss/jboss-eap-7.2" \
    JAVA_OPTS="-Djava.io.tmpdir=/tmp -Djava.net.preferIPv4Stack=true -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0"

ARG ADMIN_USERNAME='admin'
ARG ADMIN_PASWORD='admin!123'

ADD ./jboss-eap-7.2.0.zip /tmp/eap.zip
ADD ./config.cli /tmp/config.cli

USER root

RUN yum update -y \
    && yum clean all \
    && usermod -a -G 0 jboss \
    && unzip /tmp/eap.zip -d /opt/jboss \
    && mv /tmp/config.cli ${JBOSS_HOME}/config.cli \
    && ${JBOSS_HOME}/bin/add-user.sh ${ADMIN_USERNAME} ${ADMIN_PASWORD} \
    && ${JBOSS_HOME}/bin/jboss-cli.sh --file=${JBOSS_HOME}/config.cli \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R 770 ${JBOSS_HOME} \
    && rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history/* \
    && rm -rf /tmp/*

USER jboss

EXPOSE 8080 8443 9990 9999 57188

VOLUME ${JBOSS_HOME}/standalone/deployments

ENTRYPOINT ["/bin/sh", "-c"]

CMD ["${JBOSS_HOME}/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 --debug 57188"]