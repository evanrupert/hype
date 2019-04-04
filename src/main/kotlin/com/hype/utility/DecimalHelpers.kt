package com.hype.utility

import java.math.BigDecimal

fun decimal(num: Double): BigDecimal = BigDecimal.valueOf(num).setScale(2)