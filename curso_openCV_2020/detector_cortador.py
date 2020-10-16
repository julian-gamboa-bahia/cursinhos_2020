from imageai.Detection import ObjectDetection
import os
import sys


fonte=sys.argv[1]
saida=sys.argv[2]


execution_path = os.getcwd()

detector = ObjectDetection()
detector.setModelTypeAsRetinaNet()
detector.setModelPath( os.path.join(execution_path , "resnet50_coco_best_v2.0.1.h5"))
detector.loadModel()

detections, extracted_images = detector.detectObjectsFromImage(input_image=os.path.join(execution_path , fonte), output_image_path=os.path.join(execution_path , saida), extract_detected_objects=True)

for eachObject in detections:
    print(eachObject["name"] , " : " , eachObject["percentage_probability"] )