package afifo

import chisel3._
import chisel3.util._

class MemReadPortIO[T <: Data](private val gen: T, val addrWidth: Int) extends Bundle {
    require(addrWidth > 0)
    val rctrl = new MemReadPortCtrlIO(addrWidth)
    val rbits = Input(gen.cloneType)
}
