FROM oracle/graalvm-ce:19.2.1 as graalvm
COPY . /home/app/open-tracking-mn-gr
WORKDIR /home/app/open-tracking-mn-gr
RUN gu install native-image
RUN native-image --no-server -cp target/open-tracking-mn-gr-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/open-tracking-mn-gr .
ENTRYPOINT ["./open-tracking-mn-gr"]
