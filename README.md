# animator

This was the final project for CS 3500 - Object-Oriented Design in Spring 2019.

The assignment description is at these links:
```
course.ccs.neu.edu/cs3500f18/hw_05_assignment.html
course.ccs.neu.edu/cs3500f18/hw_06_assignment.html
course.ccs.neu.edu/cs3500f18/hw_07_assignment.html
```

---

To run this project, you can use the provided jar (or compile it yourself) and use the following command line argument structure:

`java -jar animator.jar -in "name-of-animation-file" -view "type-of-view" -out "where-output-show-go" -speed "integer-ticks-per-second"`

There are example animation files in the repo.

The following views are supported:
* text
* svg
* visual
* edit

The -out argument is only necessary for the text and svg views as they will create a file.

The -speed argument is only necessary for the visual and edit views.

---

Views description:

The text view: 
* will output a text file (in the same folder as the jar) description of an animation
* this is the same text description that is used to play the animation

The svg view:
* will output an svg file (in the same folder as the jar) that will play the animation
* can be opened in Chrome once created (or potentially other browsers) to see the animation

The visual view:
* will open a window that will play the animation once and automatically close

The edit view:
* will open a window with the animation playing and have an option to interact with the animation
* has the following controls:
  * Play/Pause - plays/pauses
  * Restart - restarts the animation (starts playing again if paused)
  * Loop - enable/disable looping of animation
  * Speed Up - increases speed of animation
  * Speed Down - decreases speed of animation
  * Add Shape - adds a shape by type (Rectangle or Oval) and name
  * Remove Shape - removes a shape by name
  * Add Keyframe - makes a shape be editable at a certain timepoint
  * Edit Keyframe - edits a shape at a certain time
  * Remove Keyframe - removes editability of a shape at a certain timepoint
  * Save - saves current animation as text or svg file
  * Load - replaces current animation with one loaded from text file

---

Shape editing is confusing so this is the documentation I wrote to make sense of it myself:

String addKeyframe(String name, int time);
// name represents the shape to be adding keyframe to
// time represents when to add the keyframe
// returns a string so the user will get a useful success or error message
Adding a keyframe doesn't specify any shape info, instead it just infers
shape info wherever possible. If the shape is already animated, then there are 4 cases.
First, the inputted time is before the start of the animation.
Then a new motion is added from the inputted time to the start of the animation,
extending the shape information at the start of the animation.
Second, the inputted time is after the end of the animation.
Then a new motion is added from the end of animation to the inputted time,
extending the shape information at the end of the animation.
Third, the inputted time is on an existing keyframe. Do nothing except
inform the user of this revelation. Fourth, the inputted time is in the middle
of an existing motion. Then create two motions, and insert them in place of that motion.
If we have Motion (Start x, End y), then we replace it with
Motion1 (Start x, End inputted time) and Motion2 (Start inputted time, End y),
and use the tween function to get where the shape should be at the inputted time.
The other case is if the shape is not yet animated.
There are two fields storing a preliminary time and shape information for a shape.
They are initialized to dummy values when a Shape is constructed.
To successfully animate a shape, a specific sequence of events must happen.
First, a keyframe is added for a time. This sets the preliminary time to that time.
Second, that keyframe MUST be edited, based on its time.
This sets the preliminary shape information to whatever is inputted.
Finally, another keyframe must be added. This creates the first motion,
from the lesser of the two times to the greater of the two times, with the
motion starting and ending at the preliminary shape information.

String editKeyframe(String name, int time, ShapeInfo info);
//name and time are the same as above
//ShapeInfo is the position, color, and size you want the shape to be at the given time
Editing a keyframe has two cases as well. If there are motions,
then it is checked if there is a keyframe at that time. If there is a
keyframe at that time, then it is edited to the inputted ShapeInfo.
If there is not a keyframe at that time, then there is an error message to the user.
If there are no motions, then it is checked whether the time matches the preliminary
keyframe, and the preliminary shape info doesn't exist yet. If so, then the preliminary
shape info is set. Otherwise, an error message is outputted to the user.

String deleteKeyframe(String name, int time);
//name and time are the same as above
Deleting a keyframe has two cases as well.
If there are motions, then there are the following cases.
First, it is checked whether the start of the first motion matches the given time.
If it does, then the first motion is deleted. Second, it is checked whether the
end of the last motion matches the given time. If it does, then the last motion is deleted.
Third, it is checked whether there is any other keyframe matching the time. If so,
imagine the two Motions to be Motion1 (Start x, End y) and Motion2 (Start y, End z).
Then they are replaced with a single Motion (Start x, End z). Finally, if none of
these pass, then an error message is outputted.



