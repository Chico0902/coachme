import os

os.environ["CUDA_DEVICE_ORDER"] = "PCI_BUS_ID"
os.environ["CUDA_VISIBLE_DEVICES"] = "0"

import torch
from transformers import ViTImageProcessor
from tqdm import tqdm
import cv2
import numpy as np
from moviepy.editor import VideoFileClip, concatenate_videoclips
import moviepy.editor
moviepy.editor.concatenate_videoclips.use_gpu = True

from v2021 import SummaryModel


def edit_video_with_ai(key, isEdit):
    preprocessor = ViTImageProcessor.from_pretrained(
        "google/vit-base-patch16-224", size=224, device='cuda'
    )

    SAMPLE_EVERY_SEC = 2

    video_path = 'videos/'+key+'_sample.mp4'

    cap = cv2.VideoCapture(video_path)

    n_frames = cap.get(cv2.CAP_PROP_FRAME_COUNT)
    fps = cap.get(cv2.CAP_PROP_FPS)

    video_len = n_frames / fps

    print(f'Video length {video_len:.2f} seconds!')

    frames = []
    last_collected = -1

    while cap.isOpened():
        ret, frame = cap.read()
        if not ret:
            break

        timestamp = cap.get(cv2.CAP_PROP_POS_MSEC)
        second = timestamp // 1000

        if second % SAMPLE_EVERY_SEC == 0 and second != last_collected:
            last_collected = second
            frames.append(frame)

    features = preprocessor(images=frames, return_tensors="pt")["pixel_values"]

    print(features.shape)

    model = SummaryModel.load_from_checkpoint('summary.ckpt')
    model.to('cuda')
    model.eval()

    features = features.to('cuda')

    y_pred = []

    for frame in tqdm(features):
        y_p = model(frame.unsqueeze(0))
        y_p = torch.sigmoid(y_p)

        y_pred.append(y_p.cpu().detach().numpy().squeeze())

    y_pred = np.array(y_pred)

    THRESHOLD = 0.45

    total_sec = 0

    editpoints = []
    for i, y_p in enumerate(y_pred):
        sec = i * SAMPLE_EVERY_SEC

        if y_p >= THRESHOLD:
            print("sec: ", sec)
            editpoints.append(sec)
            total_sec += SAMPLE_EVERY_SEC

    if isEdit is False:
        return editpoints

    else:
        if total_sec == 0:
            return 0

        clip = VideoFileClip(video_path)

        subclips = []

        for i, y_p in enumerate(y_pred):
            sec = i * SAMPLE_EVERY_SEC

            if y_p >= THRESHOLD:
                subclip = clip.subclip(sec, sec + SAMPLE_EVERY_SEC)
                subclips.append(subclip)

        result = concatenate_videoclips(subclips)

        result.write_videofile("results/"+key+"_AI.mp4")
