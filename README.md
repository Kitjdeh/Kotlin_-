# Kotlin_Coinmonitorin
## 서비스
- 실시간 코인 모니터링 서비스
- 첫 실행 시 즐겨찾기로 확인할 코인 설정
![AnyConv com__로딩1](https://github.com/Kitjdeh/Kotlin_Coinmonitoring/assets/109275661/f2d215cd-07c0-4930-bdc5-2389d42505ee)

- 즐겨찾기로 구분된 코인 리스트
![AnyConv com__즐겨찾기1](https://github.com/Kitjdeh/Kotlin_Coinmonitoring/assets/109275661/08667793-b9da-4eaa-be84-811d3874eb4f)

- 즐겨찾기한 코인들의 15분전 30분전 45분전 가격정보 누적
![AnyConv com__변동모양](https://github.com/Kitjdeh/Kotlin_Coinmonitoring/assets/109275661/d7868d5b-fec8-4e1b-b5bb-ffd0673f955e)

- 알림창에 실시간으로 코인 정보 확인기
![AnyConv com__알림](https://github.com/Kitjdeh/Kotlin_Coinmonitoring/assets/109275661/e1fdbcca-5f98-4dbb-8f5b-d3a2ecb728d7)


## 사용기술
Room
- 내부 DB 관리
Coroutine
- 네트워크 리퀘스트 및 내부 저장소 접근
Datastore
- 사용자 재접속 판별을 위한 내부 저장소
Lottie
- 앱 실행시 로딩 스피너에 애니메이션 효과
Retrofit2
- 코인 데이터 통신
Navigation
- 프레그먼트간의 이동
Timber
- 릴리즈 모드에서 로그가 남지 않도록 설정
SpalshScreen
- 로딩 화면 설정
Livedata
- LifeCycle 확인을 통한 데이터 및 UI 유지
Workmanager
- 백그라운드에서도 누적 데이터 확보를 위한 실행 예약
ForegroundService
- 사용자 Notification 생성


## 수행 역할
- Model -View- ViewModel의 구조로 데이터 및 레이아웃 연동

- Foreground Service로 항상 떠있는 Notification 설정

- Workmanager를 이용해 데이터 저장 예약으로 누적데이터 생성

- Room과 Coroutine Flow, LiveData를 이용한 DB 설정 및 관리

- Datastore를 이용하여 사용자의 재접속 여부 판별

- DB호출에 필요한 쿼리들을 모은 DBRepository 설정으로 DAO 사용성 증가

- Bottom Navigation 기능으로 하단 탭을 통한 페이지 이동 설정
