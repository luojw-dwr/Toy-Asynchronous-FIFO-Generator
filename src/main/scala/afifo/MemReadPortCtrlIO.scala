package afifo

import chisel3._
import chisel3.util._

class MemReadPortCtrlIO(val addrWidth: Int) extends Bundle {
    require(addrWidth > 0)
    val rclk  = Output(Clock())
    val raddr = Output(UInt(addrWidth.W))
}
