import jetbrains.buildServer.configs.kotlin.v2019_2.*
import project.production.ParkingLotProductionProject
import project.production.vcs.ParkingLotProductionVcsRoot

version = "2021.1"

project {

    vcsRoot(ParkingLotProductionVcsRoot)

    subProject(ParkingLotProductionProject)
}
