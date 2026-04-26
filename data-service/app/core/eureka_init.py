import os
import socket
import py_eureka_client.eureka_client as eureka_client
from dotenv import load_dotenv

load_dotenv()

HOSTNAME = socket.gethostname()
PORT = os.getenv("PORT")
APP_NAME = os.getenv("APP_NAME")
EUREKA_SERVER = os.getenv("EUREKA_SERVER")
INSTANCE_ID = f"{HOSTNAME}:{APP_NAME}:{PORT}"


async def register_eureka():
    await eureka_client.init_async(
        eureka_server=EUREKA_SERVER,
        app_name=APP_NAME,
        instance_host=HOSTNAME,
        instance_port=int(PORT),
        instance_id=INSTANCE_ID,
    )
