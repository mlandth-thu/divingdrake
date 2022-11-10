# Diving Drake

## Description
This is a tiny **java-fx** application that was inspired by the famous game flappy bird.<br>
The drake has to swim against a hefty wind blowing from the north and has to care not to float off the map.<br>
The drake is following the stream down the river, but must avoid logs crossing his way.<br>
The longer the drake can swim without colliding with an obstacle, the more *frequently* obstacles will spawn 
and also *move faster*. <br>
This game has **no ending**; when the drake collides with an obstacle, the score will be *reset* 
(and so will the speed and the spawning time of the obstacles).

The drake can swim upwards by pressing "w" and downwards by pressing "s".

## Sources
Code base from:
https://github.com/Da9el00/FlappyBirdJavaFX

Drake image:
https://destin.itch.io/drake

Sounds:
https://freesound.org/

### Own contributions
- changed view
- add an image for the drake
- added moving up and down for the drake
- added randomized color of logs
- added randomized width of logs
- added increasing moving speed of the obstacles 
- added increasing spawning speed of the obstacles 
- added swim sound
- added collision sound (incl playing limiter)
- removed unnecessary features / code


## TODO
- Add diving capability for drake
- (add diving animation)
- Add more obstacles apart from logs (e.g. rocks)
- tweak values

## Images
  ![plot](images/img.png)