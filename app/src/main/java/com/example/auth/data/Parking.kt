package com.example.exo2




data class Parking(
    val id: Int,
    val name: String,
    val emptyBlocks : Int,
    val city : String,
    val price : Int,
    val adress: String,
    val description: String
)


fun getData():List<Parking>{
    val dogs = ArrayList<Parking>()
    for(i in 0..29) {
        dogs.add(Parking(i, Names[i],5,"${cities[i]}, Algerie", 2000, addresses[i], "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Harum laborum expedita consectetur aliquam id laudantium possimus tempora facilis vero dolores eum rem sapiente quaerat nostrum quibusdam atque dolorem incidunt accusamus, recusandae animi optio, quia soluta. Voluptate non quibusdam, minus qui dolorum sapiente placeat adipisci molestias veniam blanditiis alias, aut rerum!"))
    }
    return dogs
}



val Names = arrayListOf(
    "Sunset Parking",
    "City Center Garage",
    "Green Oasis Park",
    "Metro Plaza",
    "Lakeside Park",
    "Skyline Tower",
    "Harborfront",
    "Maple Grove",
    "Golden Gate",
    "Downtown",
    "Meadowview Lot",
    "Oceanfront Valet",
    "Pine Grove Center",
    "Highland Heights",
    "Riverside Park",
    "Uptown Valet",
    "Central Hub",
    "Mountain View Garage",
    "Palm Oasis",
    "Urban Express",
    "Valley Vista Park",
    "Silver Springs Garage",
    "Countryside Park",
    "Civic Center",
    "Harmony Haven Garage",
    "Regal Plaza",
    "Cobblestone Park",
    "Midtown Valet",
    "Harvest Haven",
    "Meadowview Lot",
)


val cities = arrayListOf(
    "Algiers", "Oran", "Constantine", "Annaba", "Batna",
    "Blida", "Sétif", "Tizi Ouzou", "Béjaïa", "Biskra",
    "Tlemcen", "Tiaret", "Chlef", "Skikda", "Béchar",
    "Guelma", "Tébessa", "Ouargla", "Sidi Bel Abbès", "Bordj Bou Arréridj",
    "Jijel", "Tamanrasset", "M'Sila", "Mascara", "Mostaganem",
    "El Oued", "Relizane", "Ghardaïa", "Aïn Témouchent","Aïn Témouchent"
)

val addresses = arrayListOf(
    "123 Rue de l'Indépendance, Alger",
    "456 Avenue Ahmed Zabana, Oran",
    "789 Boulevard Mohamed Boudiaf, Constantine",
    "101 Rue Ibn Khaldoun, Annaba",
    "202 Avenue Colonel Amirouche, Batna",
    "303 Rue Hassiba Ben Bouali, Blida",
    "404 Boulevard Frantz Fanon, Sétif",
    "505 Rue Colonel Si Amirouche, Tizi Ouzou",
    "606 Avenue du 1er Novembre, Béjaïa",
    "707 Rue Ahmed Ben Bella, Biskra",
    "808 Boulevard El Messadia, Tlemcen",
    "909 Rue Ahmed Bey, Tiaret",
    "111 Rue Ahmed Bougara, Chlef",
    "222 Boulevard du 20 Août 1955, Skikda",
    "333 Avenue Emir Abdelkader, Béchar",
    "444 Rue Colonel Amirouche, Guelma",
    "555 Rue Ibn Khaldoun, Tébessa",
    "666 Avenue Emir Abdelkader, Ouargla",
    "777 Boulevard Sidi Bel Abbès, Sidi Bel Abbès",
    "888 Rue Mohamed Larbi Ben M'hidi, Bordj Bou Arréridj",
    "999 Boulevard Mohamed Khemisti, Jijel",
    "1010 Rue Emir Abdelkader, Tamanrasset",
    "1111 Avenue Ahmed Ben Bella, M'Sila",
    "1212 Rue Frères Boughera El Ouafi, Mascara",
    "1313 Boulevard Colonel Lotfi, Mostaganem",
    "1414 Avenue Ould Kablia, El Oued",
    "1515 Rue Mohamed Seddik Benyahia, Relizane",
    "1616 Boulevard du 1er Novembre 1954, Ghardaïa",
    "1717 Avenue Khiat Ahmed, Aïn Témouchent",
    "1212 Rue Frères Boughera El Ouafi, Mascara",
    "1313 Boulevard Colonel Lotfi, Mostaganem",
    "1414 Avenue Ould Kablia, El Oued",
    "1515 Rue Mohamed Seddik Benyahia, Relizane",
    "1616 Boulevard du 1er Novembre 1954, Ghardaïa",
    "1717 Avenue Khiat Ahmed, Aïn Témouchent"

)
