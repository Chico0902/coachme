from typing import List
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from access_S3 import download, upload, remove
from use_ai import edit_video_with_ai


class Item(BaseModel):
    key: str


class ResponseURL(BaseModel):
    url: str


class ResponseList(BaseModel):
    points: List[int]


app = FastAPI()


@app.post("/ai/edit", response_model=ResponseURL)
async def edit_video(item: Item):
    try:
        download(item.key)
        if edit_video_with_ai(item.key, True) == 0:
            ResponseURL.url = "HighLight is zero."
        else:
            ResponseURL.url = upload(item.key)
        remove(item.key)
        return ResponseURL
    except:
        raise HTTPException(status_code=500, detail="Internal Server Error")


@app.post("/ai/editpoint", response_model=ResponseList)
async def get_edit_point(item: Item):
    try:
        download(item.key)
        ResponseList.points = edit_video_with_ai(item.key, False)
        remove(item.key)
        return ResponseList
    except:
        raise HTTPException(status_code=500, detail="Internal Server Error")
