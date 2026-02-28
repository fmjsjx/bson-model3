require_relative 'generator/model_generator'
require 'fileutils'

class Generator

  attr_reader :config, :out_dir

  def initialize(config, out_dir)
    @config = config
    @out_dir = out_dir
  end

  def generate
    generate_package_dir
    # TODO
  end

  private
  def generate_package_dir
    package_dir = File.join(out_dir, File.join(config.package.split('.')))
    unless File.directory?(package_dir)
      puts "Creating package directory: #{@config.package} => #{package_dir}"
      FileUtils.mkdir_p(package_dir)
      puts "OK"
    end
  end

end
