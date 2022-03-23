package project.production.vcs

import jetbrains.buildServer.configs.kotlin.v2019_2.RelativeId
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object ParkingLotProductionVcsRoot : GitVcsRoot({

    id = RelativeId(name)
    name = javaClass.simpleName
    url = "https://github.com/thefrsh/parking-lot-intive-patronage.git"
    branch = "refs/heads/production"
    branchSpec = "+:refs/heads/"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa_teamcity"
    }
})
