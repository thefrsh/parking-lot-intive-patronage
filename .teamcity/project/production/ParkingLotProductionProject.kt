package project.production

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import project.production.build.docker.ParkingLotProductionDockerBuild
import project.production.vcs.ParkingLotProductionVcsRoot

object ParkingLotProductionProject : Project({

    name = "Production"

    vcsRoot(ParkingLotProductionVcsRoot)

    buildType(ParkingLotProductionDockerBuild)
})
