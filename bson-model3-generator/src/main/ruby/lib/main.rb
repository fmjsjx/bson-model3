#!/usr/bin/env ruby


require_relative 'configurations'
require_relative 'generator'
require 'yaml'

config = Configurations.from(YAML.load_file(ARGV[0]))

puts "package: #{config.package}"
puts ""

config.models.each do |model|
  puts "------------------------------------------"
  puts "model.package: #{model.package}"
  puts "model.name: #{model.name}"
  puts "model.type: #{model.type}"
  puts "model.consts:"
  model.consts.each do |const|
    puts "  #{const.name}: #{const.type} = #{const.value}"
  end
  puts "model.fields:"
  model.fields.each do |field|
    puts "  #{field.index} - #{field.name}, #{field.store_name}, #{field.display_name}: #{field.type}  ==> #{field.instance_variables.filter { |var| not %w[@index @name @store_name @display_name @type].include?(var.to_s) }.map { |var| "#{var}=#{field.instance_variable_get(var)}" }.join(', ')}"
  end
end
puts "==============================================="
puts ""
generator = Generator.new(config, ARGV[1])
generator.generate

puts ""

puts "Done."
