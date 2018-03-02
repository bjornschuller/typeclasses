//The trait describes the functionality
trait Showable[A] {
  def show(a: A): String
}

//Here we specify the implementations for each TYPE we care about
object Showable {

  def show[A](a: A)(implicit s: Showable[A]): String = s.show(a)

  // Holds the implementation of the Showable trait for an Int,
  // making it implicit to avoid  calling it explicitly (reducing boilerplate)
  implicit val showInt: Showable[Int] =
    new Showable[Int]{
      def show(int: Int): String = s"int $int"
    }
}

Showable.show(20) //compiler finds the showInt functionality since it is marked as implicit


//SYNTASTIC SUGAR FOR THE SAME
trait Showable2[A] {
  def show(a: A): String
}

object Showable2 {
  def apply[A](implicit s: Showable2[A]) = s  // specified in the apply method tells that in every Showable2 Type an implicit Showable2[A] should be in scope

  def show[A: Showable2](a: A) = Showable2.apply[A].show(a) //[A: Showable2] is context bound syntax => used to declare that for some type A, there is an implicit value of type Showable2[A]
  //same as: Showable2[A: Showable2].show(a)

  implicit val showInt: Showable2[Int] =
  new Showable2[Int]{
    def show(int: Int): String = s"int $int"
  }

  implicit val showString: Showable2[String] =
    new Showable2[String]{
      def show(str: String): String = s"string $str"
    }


  //allows us to call 20.show instead of show(20)
  implicit class ShowOps[A: Showable2](a: A) {
    def show = Showable2[A].show(a) //when calling .show it wraps the Showable2 around the value
  }

}

import Showable2._ //should be in scope otherwise, Showable2.show(20)
show(20) //compiler finds the showInt functionality since it is marked as implicit
20.show
show("hello")
"hello".show
// show(true) //gives compile error since we didnt create an implicit for it.