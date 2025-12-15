package shared.geometry3d

data class Intersection3d(
    val rays: Set<IntersectedRay3d>
) {
    fun intersectionTimes(): List<Double> = rays.map { it.time }
    fun intersectionPoint(): Point3d = rays.first().let { it.ray.at(it.time) }
}