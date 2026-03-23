require_relative 'store_data/class_generator'

module StoreData

  def self.generate_class_code(config, model_conf)
    ClassGenerator.new(config, model_conf).generate
  end

end