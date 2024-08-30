<div align='center'>

## Introduction
  
</div>

`Spring Boot`의 원리를 보고, 이를 JSP에 적용하여 구현한 프로젝트다. `Reflection`을 기반으로 제작하였고 `Spring`이 `Bean`을 싱글톤으로 컨테이너에서 관리한다는 것에서 영감을 받았다.
`Annotation`을 기반으로 클래스 등을 탐색하고 이를 구현한 뒤 Map에 담아서 적절한 Matching에 따라서 Reflection을 통해 자동으로 연결/실행하는 구조로 작성되었다.
`Spring Boot`의 내부도 `Reflection`으로 동작하는 것으로 추측되며, Spring Boot가 다른 프레임워크에 비하여 무거운 이유, 그렇지만 편리한 이유를 알 수 있었다. 
또한 이 프로젝트로 Spring Boot의 감수성을 약간이라도 느낄 수 있어 좋았던 프로젝트였다.
> 간단한 명함 관리 기능을 제공한다.
