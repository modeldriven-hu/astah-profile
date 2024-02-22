# Astah UML Profile PlugIn

Currently Astah SysML is not able to create custom profiles with tags, only internal profiles, however, it is able to import external profiles. One way to create a profile is to use Eclipse UML, described on the following page:

https://wiki.eclipse.org/MDT/UML2/Introduction_to_UML2_Profiles

It would be beneficial to have the profile editing capability inside Astah, instead of needing to open a 3rd party tool.

# Screenshot

![image](https://github.com/modeldriven-hu/astah-profile/assets/8182138/541152fb-14d9-4529-a405-246d8d907061)

# Functions

 - Create a new profile
 - Load profile from file
 - Save profile to file
 - Add stereotypes to the profile
 - Set metaclass for stereotype
 - Add tags to stereotypes
 - Set type for a tag
 - Apply changes to existing model file
  - A copy will be created from the original model file
  - Only create stereotype and create property actions are applied
 
