# 💡 Skill Stack  

## - Language
![Static Badge](https://img.shields.io/badge/Java_17-gray?logo=oracle&logoColor=%23F80000)
![Static Badge](https://img.shields.io/badge/JavaScript-gray?logo=javascript&logoColor=%23F7DF1E)
![Static Badge](https://img.shields.io/badge/CSS3-gray?logo=css3&logoColor=%231572B6)
![Static Badge](https://img.shields.io/badge/HTML5-gray?logo=html5&logoColor=%23E34F26)
![Static Badge](https://img.shields.io/badge/Python_3.11-gray?logo=python&logoColor=%233776AB)
<br/>

## - Cooperation
![Static Badge](https://img.shields.io/badge/Git-gray?logo=git&logoColor=%23F05032)
![Static Badge](https://img.shields.io/badge/GitLab-gray?logo=gitlab&logoColor=%23FC6D26)
![Static Badge](https://img.shields.io/badge/Jira-gray?logo=jirasoftware&logoColor=%230052CC)
![Static Badge](https://img.shields.io/badge/jenkins-gray?logo=jenkins&logoColor=%23D24939)
![Static Badge](https://img.shields.io/badge/Mattermost-gray?logo=mattermost&logoColor=%230058CC)
![Static Badge](https://img.shields.io/badge/KakaoTalk-gray?logo=kakaotalk&logoColor=%23FFCD00)
![Static Badge](https://img.shields.io/badge/Discord-grey?logo=discord&logoColor=%235865F2)
![Static Badge](https://img.shields.io/badge/Figma-gray?logo=figma&logoColor=%23F24E1E)
![Static Badge](https://img.shields.io/badge/Notion-gray?logo=notion&logoColor=white)
<br/>

## - Tools
![Static Badge](https://img.shields.io/badge/IntelliJ-gray?logo=intellijidea&logoColor=white)
![Static Badge](https://img.shields.io/badge/VSCode-gray?logo=visualstudiocode&logoColor=%23007ACC)
![Static Badge](https://img.shields.io/badge/Workbench-gray?logo=mysql&logoColor=%234479A1)
![Static Badge](https://img.shields.io/badge/Gradle-gray?logo=gradle&logoColor=%2302303A)
<br/>

## - Framework
![Static Badge](https://img.shields.io/badge/Spring_Boot-v3.1-blue?logo=springboot)
![Static Badge](https://img.shields.io/badge/Vue.Js-v3.4.15-blue?logo=vue.js&logoColor=%234FC08D)
![Static Badge](https://img.shields.io/badge/FastAPI-v0.109.0-blue?logo=fastapi&logoColor=%23009688)
<br/>

## - InfraStructure
![Static Badge](https://img.shields.io/badge/EC2-Ubuntu%2020.04-blue?logo=amazonec2&logoColor=%23FF9900)
![Static Badge](https://img.shields.io/badge/NginX-v1.18.0-blue?logo=nginx&logoColor=%23009639)
![Static Badge](https://img.shields.io/badge/Redis-v5.0.7-blue?logo=redis&logoColor=%23DC382D)
![Static Badge](https://img.shields.io/badge/MySQL-v8.0.36-blue?logo=mysql&logoColor=%234479A1)
![Static Badge](https://img.shields.io/badge/Docker-v25.0.3-blue?logo=docker&logoColor=%232496ED)
![Static Badge](https://img.shields.io/badge/OpenVidu-v2.29.0-blue?logo=webrtc&logoColor=yellow)
![Static Badge](https://img.shields.io/badge/jenkins-v2.441-blue?logo=jenkins&logoColor=%23D24939)
![Static Badge](https://img.shields.io/badge/MovieMasher-v5.1.1-blue)
<br/>
<br/>
<br/>

# 📖 Porting Manual

## 1. NginX &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/NginX-v1.18.0-blue?logo=nginx&logoColor=%23009639)

- install
    ```
    $ sudo apt-get update
    $ sudo apt-get install nginx
    $ sudo systemctl start nginx
    ```

- settings
    </br> 1) letsencrypt
    ```
    # 1. install snapd
    $ sudo apt-get install snapd

    # 2. install certbot
    $ sudo snap install --classic certbot

    # 3. Prepare the Certbot command
    $ sudo ln -s /snap/bin/certbot /usr/bin/certbot

    # 4. run Certbot
    $ sudo certbot --nginx

    # 5. Test automatic renewal
    $ sudo certbot renew --dry-run
    ```
    </br> 2) nginx configuration
    ```
    $ sudo vi /etc/nginx/sites-available/default
    ```
    ---
    #### `/etc/nginx/sites-available/default`
    ```
    server {
        listen 443 default_server;
        listen [::]:443 default_server;

        server_name i10A403.p.ssafy.io;

        ssl on;
        ssl_certificate /etc/letsencrypt/live/i10a403.p.ssafy.io/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/i10a403.p.ssafy.io/privkey.pem;

        root /var/www/CoachMe/dist;
        index index.html;

        location / {
                try_files $uri $uri/ /index.html;
        }

        location /openvidu {
                proxy_pass http://127.0.0.1:5443;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
        }

        location /api {
                proxy_http_version 1.1;
                proxy_pass http://127.0.0.1:7777;
                proxy_set_header X-Real_IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_redirect off;
        }

        location /ai {
                proxy_http_version 1.1;
                proxy_pass http://127.0.0.1:8000;
                proxy_set_header X-Real_IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";

                # 프록시 버퍼 설정 - no 502 errors!
                proxy_buffer_size               128k;
                proxy_buffers                   4 256k;
                proxy_busy_buffers_size         256k;

                proxy_connect_timeout 500;
                proxy_send_timeout 500;
                proxy_read_timeout 500;
                send_timeout 500;
        }

        location /edit/ {
                proxy_http_version 1.1;
                proxy_pass http://127.0.0.1:8570/;
                proxy_set_header X-Real_IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
                proxy_set_header X-Forwarded-Proto $scheme;

        }
    }

    server {
        listen 80;
        listen [::]:80;

        server_name i10A403.p.ssafy.io;

        return 301 https://$server_name$request_uri;
    }
    ```
- reload
    ```
    $ sudo systemctl reload nginx
    ```

<br/>

## 2. Redis &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/Redis-v5.0.7-blue?logo=redis&logoColor=%23DC382D)
- install
    ```
    $ sudo apt-get update
    $ sudo apt-get install redis-server
    $ sudo systemctl start redis-server
    ```

<br/>

## 3. MySQL &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/MySQL-v8.0.36-blue?logo=mysql&logoColor=%234479A1)
- install
    ```
    $ sudo apt-get update
    $ sudo apt-get install mysql-server
    $ sudo systemctl start mysql-server
    ```
- settings
    ```
    $ sudo mysql -u root
    mysql> create user '아이디'@'접속호스트' identified by '비밀번호';
    myslq> create database 데이터베이스명;
    mysql> grant all privileges on 데이터베이스명.* to '아이디'@'접속호스트';
    mysql> flush privileges;
    ```

    
<br/>

## 4. Docker &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/Docker-v25.0.3-blue?logo=docker&logoColor=%232496ED)
- install
    ```
    # Add Docker's official GPG key:
    $ sudo apt-get update
    $ sudo apt-get install ca-certificates curl
    $ sudo install -m 0755 -d /etc/apt/keyrings
    $ sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
    $ sudo chmod a+r /etc/apt/keyrings/docker.asc

    # Add the repository to Apt sources:
    $ echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://  download.docker.com/linux/ubuntu \
    $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    $ sudo apt-get update

    # install the Docker Packages:
    $ sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    ```
    
<br/>

## 5. OpenVidu &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/OpenVidu-v2.29.0-blue?logo=webrtc&logoColor=yellow)
- install
    ```
    $ sudo su
    # cd /opt
    # curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_2.29.0.sh | bash 
    ```
- settings
    ```
    # cd /opt/openvidu
    # vi .env
    ```
    ---
    #### `/opt/openvidu/.env`

    ```
    # OpenVidu configuration
    # ----------------------
    # Documentation: https://docs.openvidu.io/en/stable/reference-docs/openvidu-config/

    # NOTE: This file doesn't need to quote assignment values, like most shells do.
    # All values are stored as-is, even if they contain spaces, so don't quote them.

    # Domain name. If you do not have one, the public IP of the machine.
    # For example: 198.51.100.1, or openvidu.example.com
    DOMAIN_OR_PUBLIC_IP=도메인네임

    # OpenVidu SECRET used for apps to connect to OpenVidu server and users to access to    OpenVidu Dashboard
    OPENVIDU_SECRET=지정할 오픈비두 시크릿

    # Certificate type:
    # - selfsigned:  Self signed certificate. Not recommended for production use.
    #                Users will see an ERROR when connected to web page.
    # - owncert:     Valid certificate purchased in a Internet services company.
    #                Please put the certificates files inside folder ./owncert
    #                with names certificate.key and certificate.cert
    # - letsencrypt: Generate a new certificate using letsencrypt. Please set the
    #                required contact email for Let's Encrypt in LETSENCRYPT_EMAIL
    #                variable.
    CERTIFICATE_TYPE=letsencrypt

    # If CERTIFICATE_TYPE=letsencrypt, you need to configure a valid email for notifications
    LETSENCRYPT_EMAIL=사용자 이메일

    ...

    # Whether to enable recording module or not
    OPENVIDU_RECORDING=true    # 녹화 설정

    # Use recording module with debug mode.
    OPENVIDU_RECORDING_DEBUG=false

    # Openvidu Folder Record used for save the openvidu recording videos. Change it
    # with the folder you want to use from your host.
    OPENVIDU_RECORDING_PATH=/opt/openvidu/recordings    # 녹화본 저장 경로

    # System path where OpenVidu Server should look for custom recording layouts
    OPENVIDU_RECORDING_CUSTOM_LAYOUT=/opt/openvidu/custom-layout

    # if true any client can connect to
    # https://OPENVIDU_SERVER_IP:OPENVIDU_PORT/recordings/any_session_file.mp4
    # and access any recorded video file. If false this path will be secured with
    # OPENVIDU_SECRET param just as OpenVidu Server dashboard at
    # https://OPENVIDU_SERVER_IP:OPENVIDU_PORT
    # Values: true | false
    OPENVIDU_RECORDING_PUBLIC_ACCESS=true    # 녹화 영상 접근 시 인증 설정 유무
    ```
- start
    ```
    # cd /opt/openvidu
    # ./openvidu start
    ```
    
<br/>

## 6. Jenkins &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/jenkins-v2.441-blue?logo=jenkins&logoColor=%23D24939)
- install
    ```
    # 1. add the key to your system
    $ sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
    https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key

    # 2. add a Jenkins apt repository entry
    $ echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
    https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
    /etc/apt/sources.list.d/jenkins.list > /dev/null

    # 3.
    $ sudo apt-get update
    $ sudo apt-get install fontconfig openjdk-17-jre
    $ sudo apt-get install jenkins    
    ```
- insert secret
    ```
    $ cat /var/lib/jenkins/secrets/initialAdminPassword
    ```

<br/>

## 7. MovieMasher &nbsp;&nbsp;&nbsp;![Static Badge](https://img.shields.io/badge/MovieMasher-v5.1.1-blue)
- install
    ```
    $ sudo docker run -d -p 8570:8570 --name moviemasher moviemasher/moviemasher.js:5.1.1
    ```
