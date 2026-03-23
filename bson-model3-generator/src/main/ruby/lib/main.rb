#!/usr/bin/env ruby


require_relative 'configurations'
require_relative 'generator'
require 'yaml'

config = Configurations.from(YAML.load_file(ARGV[0]))

puts ""
generator = Generator.new(config, ARGV[1])
generator.generate

puts ""

puts "Done."
