ECommerceApp
============

[E-commerce][] application modeling for analysis and simulation in cloud computing. A good way to evaluate your management policy in a controlled environment. Besides, this model allows the production of tracelogs, scarce in cloud environments because of business and confidentiality. This project uses [CloudSim][] framework and [R][] software via [REngine][] library. 

[E-commerce]: http://rubis.ow2.org/	
[CloudSim]: http://www.cloudbus.org/cloudsim/
[R]: https://www.r-project.org/
[REngine]: http://rforge.net/org/doc/org/rosuda/JRI/Rengine.html

Features
--------

* The models can be used to support your own resource management strategy
* All statistical make decisions could be developet through R statistical language
* A resource prediction model based on Elman ANN  is provided 


Dependencies and Configuration Issues
-------------------------------------

ECommerceApp depends on:  
  1. [CloudSim][CloudSim2], a framework that enables simulation of cloud computing infrastructures;
  2. [DVFS package][DVFS], it is a CloudSim package which allows Dynamic Voltage and Frequency Scale (DVFS) simulation to support energy-eficiency resource management policies;
  3. [R][], it is a language and environment for statistical computing and graphics; 
  4. [JRI][], it is a Java/R Interface which allows to run R inside Java applications.

You can find instructions for project configuration on the ECommerceApp wiki. ECommerceApp requires CloudSim version 3.0.3. 

[CloudSim2]: https://github.com/Cloudslab/cloudsim/releases
[DVFS]: http://www.cloudbus.org/cloudsim/CloudSim_DVFS.rar
[R]: https://cran.r-project.org/mirrors.html 
[JRI]: https://rforge.net/JRI/index.html

Example
-------

The usefulness of models is demonstrated through a use case on CPU utilization forecasting.

Citation Request
----------------

Deborah Magalhães, Rodrigo N. Calheiros, Rajkumar Buyya, Danielo G. Gomes, Workload modeling for resource usage analysis and simulation in cloud computing, Computers & Electrical Engineering, Volume 47, October 2015, Pages 69-81, ISSN 0045-7906, http://dx.doi.org/10.1016/j.compeleceng.2015.08.016.
(http://www.sciencedirect.com/science/article/pii/S004579061500302X)

See [our other project][MCC] in Mobile Cloud Computing!!

[MCC]: https://github.com/UFC-GREat-PPGETI/MCCSimulator
