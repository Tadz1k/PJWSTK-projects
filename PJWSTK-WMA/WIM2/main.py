import cv2 as cv
import numpy as np
def main():
    img_to_read = 'tray8.jpg'
    img = cv.imread(img_to_read, cv.IMREAD_COLOR)  
    gray = cv.cvtColor(img,cv.COLOR_BGR2GRAY)
    edges = cv.Canny(gray,60,220)
    lines = cv.HoughLinesP(edges,1,np.pi/180,90, minLineLength=40, maxLineGap=5)
    minX, minY, maxX, maxY = lines[0][0]
    height, width, channels = img.shape
    for line in lines:
        x1,y1,x2,y2 = line[0]
        if x1 < minX: minX = x1
        if x2 < minX: minX = x2
        if x1 > maxX: maxX = x1
        if x2 > maxX: maxX = x2
        if y1 < minY: minY = y1
        if y2 < minY: minY = y2
        if y1 > maxY: maxY = y1
        if y2 > maxY: maxY = y2

    cv.line(img,(0,maxY),(width,maxY),(0,255,0),2)
    cv.line(img, (0, minY), (width, minY),(0,255,0),2)
    cv.line(img, (maxX, 0), (maxX, height),(0,255,0),2)
    cv.line(img, (minX, 0), (minX, height),(0,255,0),2)

    #Pobranie monet
    #cv.imshow('detected circles',img)
    gray = adjust_gamma(gray, 1.9)
    blurred = cv.medianBlur(gray,1)
    kernel = np.ones((5, 5), np.uint8)
    erode = cv.erode(blurred, kernel) #   
    dillation = cv.dilate(erode, kernel)#                  100           21
    coins = cv.HoughCircles(dillation,cv.HOUGH_GRADIENT,1,20, param1=120,param2=30,minRadius=0,maxRadius=39)
    coins = np.uint16(np.around(coins))
    total_5zl = 0
    total_5gr = 0
    tray_5zl = 0
    tray_5gr = 0
    for i in coins[0,:]:
        if i[2] < 33:
            cv.circle(img,(i[0],i[1]),2,(255,0,0),3)
            cv.putText(img, '5gr', (i[0]+5, i[1]), cv.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,0), 2, cv.LINE_AA)
            total_5gr += 1
            if(on_tray(minX, maxX, minY, maxY, i[0], i[1])):
                cv.circle(img,(i[0],i[1]),i[2],(255,0,0),3)
                tray_5gr += 1
        else:
            cv.circle(img,(i[0],i[1]),2,(0,0,255),3)
            cv.putText(img, '5zl', (i[0]+5, i[1]), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 2, cv.LINE_AA)
            if(on_tray(minX, maxX, minY, maxY, i[0], i[1])):
                cv.circle(img,(i[0],i[1]),i[2],(255,0,0),3)
                tray_5zl += 1
            total_5zl += 1
    cv.putText(img, f'5zl : {total_5zl}x', (30, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 1, cv.LINE_AA)
    cv.putText(img, f'5gr : {total_5gr}x', (30, 70), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 1, cv.LINE_AA)
    cv.putText(img, f'5zl na tacy : {tray_5zl}x', (30, 90), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 1, cv.LINE_AA)
    cv.putText(img, f'5gr na tacy : {tray_5gr}x', (30, 110), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 1, cv.LINE_AA)
    sum_in_zl = tray_5zl * 5 + tray_5gr * 0.05
    cv.putText(img, f'suma na tacy : {sum_in_zl} zl', (30, 130), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0,0,255), 1, cv.LINE_AA)
    cv.imshow('detected circles',img)



    k = cv.waitKey(0)

def adjust_gamma(image, gamma=1.0):
	invGamma = 1.0 / gamma
	table = np.array([((i / 255.0) ** invGamma) * 255
		for i in np.arange(0, 256)]).astype("uint8")
	return cv.LUT(image, table)

def on_tray(minX, maxX, minY, maxY, pos1, pos2):
    if pos1 >= minX and pos1 <= maxX and pos2 >= minY and pos2 <= maxY:
        return True



if __name__ == '__main__':
    main()