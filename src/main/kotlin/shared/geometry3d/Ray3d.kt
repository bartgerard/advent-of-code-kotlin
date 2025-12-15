package shared.geometry3d

data class Ray3d(
    val point: Point3d,
    val direction: Vector3d
) {
    companion object {
        fun intersect(rays: List<Ray3d>): List<Point3d> = rays.flatMapIndexed { index, r1 ->
            rays.subList(index + 1, rays.size).mapNotNull { r2 -> r1 intersect r2 }
        }

        fun intersectionsFor(rays: List<Ray3d>): List<Intersection3d> = rays.flatMapIndexed { index, r1 ->
            rays.subList(index + 1, rays.size).mapNotNull { r2 -> r1 intersection r2 }
        }
    }

    /// p1 + t * v1 = p2 + s * v2
    ///
    /// t = ((p2 - p1) cross v2) dot (v1 cross v2) / ((v1 cross v2) * (v1 cross v2))
    infix fun intersect(r: Ray3d): Point3d? = intersectionTime(r)?.let { t -> at(t) }

    private fun intersectionTime(r: Ray3d): Double? {
        val p1 = point
        val v1 = direction
        val p2 = r.point
        val v2 = r.direction

        val a = v1 cross v2
        val aDot = a dot a

        if (aDot == 0.0) {
            // r1 and r2 are parallel
            return null
        }

        val b = (p2 - p1) cross v2
        return b dot a / aDot
    }

    infix fun intersection(ray2: Ray3d): Intersection3d? {
        val time1 = intersectionTime(ray2) ?: return null
        val time2 = ray2.intersectionTime(this) ?: return null

        return Intersection3d(
            setOf(
                IntersectedRay3d(this, time1),
                IntersectedRay3d(ray2, time2)
            )
        )
    }

    fun at(t: Int) = point + direction * t
    fun at(t: Double) = point + direction * t
}