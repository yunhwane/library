## Kotlin과 Jpa 함께 사용하는 팁
- setter private 하게 만드는 방법

``` kotlin
class User(
     private var name: String,
) {
     val name: String
         get() = name
}
```
- backing-property 사용하기

``` kotlin
class User(name: String) {
     var name: String = name
         private set
}
```
- custom setter 사용하기

#### 개인적으로 setter 사용을 권장하지 않는다.

### property 를 생성자에 넣느냐 ? class body 안에 넣느냐 ?
- 프로퍼티를 생성자 혹은 클래스 body 안에 구분해서 넣을때 명확한 기준에 안에서 처리한다.

#### tip 
- entity 가 생성되는 로직을 찾고 싶다면, constructor 를 활용할 수 있다.
- 