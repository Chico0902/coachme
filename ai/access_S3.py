import boto3
import os

AWS_ACCESS_KEY_ID = "AKIAZ762MW5CXHXTF56Y"
AWS_SECRET_ACCESS_KEY = "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"
AWS_DEFAULT_REGION = "ap-northeast-2"
BUCKET = "ssafycoachme"

client = boto3.client('s3',
                      aws_access_key_id=AWS_ACCESS_KEY_ID,
                      aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
                      region_name=AWS_DEFAULT_REGION
                      )


def download(key):
    file_name = 'videos/' + key + '_sample.mp4'  # 다운될 이미지 이름
    key = key + '.mp4'
    client.download_file(BUCKET, key, file_name)


def upload(key):
    file_name = 'results/' + key + '_AI.mp4'  # 업로드할 파일 이름
    key = key + '_AI.mp4'  # S3에 저장될 이름
    client.upload_file(file_name, BUCKET, key)
    return 'https://' + BUCKET + '.s3.' + AWS_DEFAULT_REGION + 'amazonaws.com/' + key


def remove(key):
    download_path = 'videos/' + key + '_sample.mp4'
    upload_path = 'results/' + key + '_AI.mp4'

    if os.path.isfile(download_path):
        os.remove(download_path)
        print("Download File Remove!!")

    if os.path.isfile(upload_path):
        os.remove(upload_path)
        print("Upload File Remove!!")
