#from collections import deque
import numpy as np  # math libraries
#import argparse  # to find and pass files
#import imutils  # resizing
import cv2  # opencv itself
#from PIL import ImageGrab  # for screen capture
#from matplotlib import pyplot as plt


def nothing(x):
    pass

# Callback Function for Trackbar (but do not any work)
# def trackCircle(mask):
#     cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]
#     if len(cnts)>0:
#         c = max(cnts, key = cv2.ContourArea)
#         ((x,y), radius) = cv2.minEnclosingCircle(c)
#         cv2.circle(frame, (int(x), int(y), 0, 255, 255, 2))



from collections import deque
h, s, v = 100, 100, 100
gauss = 5
# sets the global variables for the hue, sat, and val
#def distance_to_camera(knownWidth, focalLength, perWidth):
 #   return (knownWidth * focalLength)/perWidth
def distance(pixelRadius):
    getDistance = 1349*(pixelRadius**(-0.901))
    return(getDistance)
cv2.namedWindow('Control Panel',cv2.WINDOW_NORMAL)  # makes a control panel
# cv.CreateTrackbar(trackbarName, windowName, initial value, range, onChange)  None
cv2.createTrackbar('hue', 'Control Panel', 81, 180, nothing)  # sets the hue trackbar on the control panel
cv2.createTrackbar('sat', 'Control Panel', 255, 255, nothing)  # sets the sat trackbar on the control panel
cv2.createTrackbar('val', 'Control Panel', 182, 255, nothing)  # sets the val trackbar on the control panel
cv2.createTrackbar('range', 'Control Panel', 127, 127, nothing)
cv2.createTrackbar('srange', 'Control Panel', 91, 127, nothing)
#Where the camera location is
#device = cv2.VideoCapture("http://10.41.39.11/mjpg/video.mjpg")
img = cv2.imread("C:\\Users\\admin\\Desktop\\Coding\\Robot\\Robot\\src\\main\\java\\frc\\robot\\scratch.jpg")
cv2.namedWindow('initial frame',cv2.WINDOW_NORMAL)
#Where the camera location is

while True:
   # _, frame = device.read()
   # hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    height, width, channel = img.shape
   # frame = cv2.resize(frame, (int(width/2), int(height/2)))
    frame_width = int(width/2)
    frame_height = int(height/2)
    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (5,5),0)
    h = cv2.getTrackbarPos('hue', 'Control Panel')
    s = cv2.getTrackbarPos('sat', 'Control Panel')
    v = cv2.getTrackbarPos('val', 'Control Panel')
    r = cv2.getTrackbarPos('range', 'Control Panel') #determines how big a slice you want from the hsv py
    sr = cv2.getTrackbarPos('srange', 'Control Panel')
    #cv2.resizeWindow("Control Panel",1,1)

    lowerthreshold = np.array([h-10, s-sr, v-r])
    higherthreshold = np.array([h+10, s+sr, v+r])

    mask = cv2.inRange(hsv, lowerthreshold, higherthreshold)
    #mask = cv2.GaussianBlur(mask,(5,5), gauss)
    result = cv2.bitwise_and(img, img, mask=mask)
    cv2.imshow("Result", result)
    cnts = cv2.findContours(mask.copy(), cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]

    #if cnts point list is non empty
    if len(cnts)>0:
        #c is the biggest contour array
        c = max(cnts, key = cv2.contourArea)

        #calculate the radius and center of circle
        x,y,w,h = cv2.boundingRect(c)
        cv2.rectangle(img, (x,y), (x+w, y+h),( 255, 255,0),2)
        centerX = (x+w)/2
        centerY = (y+w)/2
        #font = cv2.FONT_HERSHEY_SIMPLEX
        #cv2.putText(frame, "X", (int(centerX),y),font,1,(255,255,255),2,cv2.LINE_AA )
        print(centerX)
        #cv2.circle(mask, (int(x), int(y)), int(radius), color[, thickness[, lineType[, shift]]])
        centerOfFrame = int(frame_width/2/2)
        threshold = 0.2*frame_width
        file = open("Data.txt", 'w')
        left = 0
        right = 0
        if(centerX >(centerOfFrame + threshold-40)):


            right = 1
            left = 0
            print("Turn Right")

        if(centerX <(centerOfFrame + threshold-40)):

            left = 1
            right = 0
            print("Turn Left")
        if(centerX == (centerOfFrame+threshold)):
            print("Continue")
        if(right == 1):
            file.write("right")
        if(left == 1):
            file.write("left")
    cv2.imshow("initial frame",img)
    cv2.imshow("mask", mask)
    #track = cv2.bitwise_and(frame,frame,mask=final)
    #cv2.imshow("Tracking", track)
    #bitwise function to implement the mask on the frame


    if cv2.waitKey(1) == 27:
        break

device.release()
cv2.destroyAllWindows()