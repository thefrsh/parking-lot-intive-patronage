package project.production

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import project.production.build.docker.ParkingLotProductionDockerBuild

object ParkingLotProductionProject : Project({

    name = "Production"

    buildType(ParkingLotProductionDockerBuild)
})
