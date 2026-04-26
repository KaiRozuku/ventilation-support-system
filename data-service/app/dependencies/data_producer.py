# from app.core.queue import queue
# from app.dependencies.data_generator import get_data_generator
# from fastapi import Depends
# from app.iot.data_producer import DataProducer
#
#
# def get_producer(generator=Depends(get_data_generator)):
#     return DataProducer(
#         queue=queue,
#         generator=lambda: generator.generate().to_dict(orient="records")[0],
#         interval=2.0
#     )
