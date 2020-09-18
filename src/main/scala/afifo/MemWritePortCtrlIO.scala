package afifo

import chisel3._
import chisel3.util._

class MemWritePortCtrlIO(val addrWidth: Int) extends Bundle {
    require(addrWidth > 0)
    val wclk  = Output(Clock())
    val we    = Output(Bool())
    val waddr = Output(UInt(addrWidth.W))
}
