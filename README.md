# Type classes
Type classes are a powerful tool used in functional programming. It provide polymorphism without using subtyping, 
but in a completely type safe way. 

Type classes represent some common functionality that can be applied to values of many different types. Therefore, we 
implement a part of the code with generic type parameters. Moreover, we don’t have to change existing types in 
order to extend them with the new functionality. 

Type classes use implicits as a mechanism for matching instances with code that uses them. Type classes come with all the benefits and costs related to implicits.
The compiler uses implicit resolution to find an instance that is the closest in the scope.

In the typeclasses.sc file you see some examples (mainly based on: https://blog.scalac.io/2017/04/19/typeclasses-in-scala.html)



sources:
https://blog.scalac.io/2017/04/19/typeclasses-in-scala.html  !!

https://typelevel.org/cats/typeclasses.html

http://danielwestheide.com/blog/2013/02/06/the-neophytes-guide-to-scala-part-12-type-classes.html

https://www.theguardian.com/info/developer-blog/2016/dec/22/parental-advisory-implicit-content

http://blog.leifbattermann.de/2017/03/25/what-are-scala-type-classes/