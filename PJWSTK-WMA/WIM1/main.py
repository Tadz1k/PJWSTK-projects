import cv2
from cv2 import resize
from cv2 import blur
import numpy as np

def main():
    video = cv2.VideoCapture('movingball.mp4')
    capture, image = video.read()
    colorfull_video = True

    while capture:
        capture, image = video.read()
        #Pobranie czerwonego koloru, konwersja na HSV
        hsv=cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        lower_red_hsv = np.array([0,50,50])#np.array([0,30,50])
        upper_red_hsv = np.array([10,255,255])#np.array([2,255,255])
        mask1=cv2.inRange(hsv, lower_red_hsv, upper_red_hsv)
        lower_red_hsv = np.array([170,50,50])
        upper_red_hsv = np.array([180,255,255])
        mask2=cv2.inRange(hsv, lower_red_hsv, upper_red_hsv)
        mask=mask1+mask2
        red=cv2.bitwise_and(image, image, mask=mask)
        #Dylatacja
        kernel = np.ones((5,5), np.uint8)
        dilation = cv2.dilate(red, kernel, iterations = 2)
        #Usuwanie szumu
        closing = cv2.morphologyEx(dilation, cv2.MORPH_CLOSE, kernel)
        #Zmiana obrazka na carno-biały
        gray = cv2.cvtColor(closing, cv2.COLOR_BGR2GRAY)
        blurred = cv2.GaussianBlur(gray, (5,5), 0)
        thresh = cv2.threshold(blurred, 60, 255, cv2.THRESH_BINARY)[1]
        #Wyszukiwanie konturu
        contours,_= cv2.findContours(mask,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)

        max_contour = contours[0]
        for contour in contours:
            if cv2.contourArea(contour)>cv2.contourArea(max_contour):
                max_contour=contour

        contour=max_contour
        M=cv2.moments(contour)
        cx=int(M['m10']/M['m00'])
        cy=int(M['m01']/M['m00'])
        cv2.circle(thresh, (cx,cy),3,(0,0,0),5)
        cv2.circle(image, (cx,cy),3,(0,0,0),5)
        if not colorfull_video:
            cv2.imshow("frame", cv2.resize(thresh, (int(image.shape[1]*0.5), int(image.shape[0]*0.5))))
        if colorfull_video:
            cv2.imshow("frame", cv2.resize(image, (int(image.shape[1]*0.5), int(image.shape[0]*0.5))))
        
        key=cv2.waitKey(1)
        if key==ord('q'):
            break
        if key==ord('w'):
            if colorfull_video:
                colorfull_video = False
            else:
                colorfull_video = True
        
    cv2.waitKey(0)
    cv2.destroyAllWindows()
        


if __name__ == '__main__':
    main()