package afifo

import chisel3._
import chisel3.util._

class MemWritePortIO[T <: Data](private val gen: T, val addrWidth: Int) extends Bundle {
    require(addrWidth > 0)
    val wctrl = new MemWritePortCtrlIO(addrWidth)
    val wbits = Output(gen.cloneType)
}
