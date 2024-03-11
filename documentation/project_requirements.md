# Java-level-up Requirements

## Deployment Requirements
- Deployed on AWS cloud
- Utilize any free tier relational database or SQL Server on AWS
- No serverless databases
- AWS infrastructure deployment scripted (e.g., terraform, AWS CloudFormation, AWS CDK)
- Automated deployment of database and database changes
- Automated deployment of Java server
- Automated deployment of Java CLI client for user download
- Build, Test, and Deployment automated (CI/CD)

## System Requirements
- Java-based server with a stateless API (REST or other, not necessarily HTTP-based); Spring not required
- Java-based CLI to interact with the stateless API

## Deliverables
- Deployed database
- Deployed Java Server
- Downloadable and runnable Java CLI client (inclusive of a tailored JDK in the download/install)
- Full unit and integration test packs (e.g., integration test pack in Postman if service is HTTP based)
- Automated unit and integration tests (CI/CD)
- Source code in a git repo accessible by ATC
- Database design documentation (ERD) in source control
- Infrastructure as code, source controlled
- Database change management code, source controlled
- CI/CD source controlled
- Complete readme on setting up the working database from scratch, source controlled
- JIRA board for project progress tracking
- Confluence pages for documentation

## Limitations
- Java 21 or newer only
- Only libraries that constitute part of Spring are allowed (e.g., https://start.spring.io/)
- Explanation required for any libraries used, including their functionality and rationale behind their usage
