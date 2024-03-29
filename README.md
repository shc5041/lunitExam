# LunitExam
실행 방법
-------------
> required : jdk - openjdk17
>>mac인 경우 chmod +x ./execute.sh 실행 한 후 `zsh: operation not permitted: ./execute.sh` 오류가 날 경우 xattr -d com.apple.quarantine ./execute.sh 를 해준다.
>> LunitExam에서 ./execute.sh를 하면 빌드 및 docker compose가 실행된다.
>> 위 docker들이 구동이 되면  http://localhost:8280/swagger-ui.html 으로 접속 하면된다. 

각 컨트롤러 설명 
-------------
1. SlideInfoController
> 파일 upload 및 upload history를 관리한다. 
>> API 순서
>>> 1. POST /api/file/upload/userId/{userId} - 파일 upload를 제공 한다. path로 userId를 건네 줘야한다. 
>>> 2. GET /api/file/all - 모든 upload 히스토리를 제공한다. 
>>> 3. GET /api/file/userId/{userId} - userId 별로 검색이 가능하며 path로 userId를 건네 줘야한다. 또, 기간별 검색을 하고 싶을때는 queryParam으로 startDateTime 및 endDateTime를 주면 된다.
>>> 4. GET /api/file/userId/{userId}/fileName/{fileName} - userId별로 검색이 가능하며, fileName은 like검색으로 결과를 가져온다. 3번과 동일한 queryParam을 가진다.
>>> 5. GET /api/file/download/idx/{idx} - 위 2, 3, 4, 5 에 나온 idx를 건네주면 관련 파일을 다운받는다. 
2. AnalysisDecisionController
> upload 한 파일을 분석 및 grid 데이터를 만들고 관련 데이터를 보기위한 컨트롤러  
>> API 순서 
>>> 1. POST /api/analysis/decision/slideInfoIdx/{idx}/userId/{userId} - SlideInfoController에 upload된 idx값과 userID를 건네주면 분석후 grid정보 및 analysis정보를 건네 받는다.
>>> 2. GET /api/analysis/userId/{userId} - userId 별로 검색이 가능하며 path로 userId를 건네 줘야한다. 또, 기간별 검색을 하고 싶을때는 queryParam으로 startDateTime 및 endDateTime를 주면 된다.
>>> 3. GET /api/analysis/fileName/{fileName} - userId별로 검색이 가능하며, fileName 결과를 가져온다. 2번과 동일한 queryParam을 가진다.
>>> 4. GET /api/analysis/all - 모든 분석 결과를 가져온다. 
3. GridAnalysisController
>  GridAnalysis의 히스토리를 보기위한 컨트롤러. 
>> API 순서
>>> 1. GET /api/grid/all - 모든 grid 데이터를 가져온다. 
>>> 2. GET /api/grid/analysisDecisionIdx/{idx} - 상위 부모의 idx값으로 검색 결과를 가져온다. 
>>> 3. GET /api/grid/userId/{userId} - userId별로 데이터를 가져오며, 또, 기간별 검색을 하고 싶을때는 queryParam으로 startDateTime 및 endDateTime를 주면 된다.


