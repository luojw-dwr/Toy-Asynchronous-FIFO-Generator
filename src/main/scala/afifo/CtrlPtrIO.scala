package afifo

import chisel3._
import chisel3.util._

class CtrlPtrIO(val ptrWidth: Int) extends Bundle {
    require(ptrWidth > 0)
    val wptr = Output(UInt(ptrWidth.W))
    val rptr = Input(UInt(ptrWidth.W))
}
