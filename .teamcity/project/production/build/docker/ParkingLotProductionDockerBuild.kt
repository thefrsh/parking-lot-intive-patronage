package project.production.build.docker

import const.ProjectConst
import const.SystemConst
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import project.production.vcs.ParkingLotProductionVcsRoot

object ParkingLotProductionDockerBuild : BuildType({

    name = "Production Docker"

    vcs {
        root(ParkingLotProductionVcsRoot)
    }

    steps {
        dockerCommand {
            name = ParkingLotProductionDockerBuildSteps.BASE
            commandType = build {
                source = file {
                    path = ProjectConst.DOCKERFILE_PATH
                }
                commandArgs = "--no-cache --target ${ParkingLotProductionDockerBuildSteps.BASE}"
            }
        }
        dockerCommand {
            name = ParkingLotProductionDockerBuildSteps.TEST
            commandType = build {
                source = file {
                    path = ProjectConst.DOCKERFILE_PATH
                }
                commandArgs = "--target ${ParkingLotProductionDockerBuildSteps.TEST}"
            }
        }
        dockerCommand {
            name = ParkingLotProductionDockerBuildSteps.PACKAGE
            commandType = build {
                source = file {
                    path = ProjectConst.DOCKERFILE_PATH
                }
                commandArgs = "--target ${ParkingLotProductionDockerBuildSteps.PACKAGE}"
            }
        }
        dockerCommand {
            name = ParkingLotProductionDockerBuildSteps.PRODUCTION
            commandType = build {
                source = file {
                    path = ProjectConst.DOCKERFILE_PATH
                }
                commandArgs = """-t parking-lot:${SystemConst.BUILD_NUMBER} 
                    --tagret ${ParkingLotProductionDockerBuildSteps.PRODUCTION}"""
            }
        }
    }
})
